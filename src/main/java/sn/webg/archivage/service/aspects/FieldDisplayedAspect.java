package sn.webg.archivage.service.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.annotations.DocumentFilter;
import sn.webg.archivage.service.annotations.DocumentTypeFilter;
import sn.webg.archivage.service.annotations.FieldDisplayedFilter;
import sn.webg.archivage.service.annotations.FolderFilter;
import sn.webg.archivage.service.annotations.FolderTypeFilter;
import sn.webg.archivage.service.annotations.IsAdmin;
import sn.webg.archivage.service.annotations.MetaDataFilter;
import sn.webg.archivage.service.annotations.MetaDataSearchFilter;
import sn.webg.archivage.service.entities.DocumentEntity;
import sn.webg.archivage.service.entities.DocumentTypeEntity;
import sn.webg.archivage.service.entities.FolderEntity;
import sn.webg.archivage.service.entities.FolderTypeEntity;
import sn.webg.archivage.service.entities.MetaDataEntity;
import sn.webg.archivage.service.entities.RoleEntity;
import sn.webg.archivage.service.entities.UserEntity;
import sn.webg.archivage.service.exceptions.ForbiddenActionException;
import sn.webg.archivage.service.models.AbstractStorage;
import sn.webg.archivage.service.repositories.UserRepository;
import sn.webg.archivage.service.security.SecurityCompositeRole;
import sn.webg.archivage.service.security.SecurityUtils;
import sn.webg.archivage.service.utils.ArchivageUtils;
import sn.webg.archivage.service.utils.DateConverter;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FieldDisplayedAspect implements DateConverter {

    private final UserRepository userRepository;

    static final String START = ".start";
    static final String END = ".end";

    @Around("@annotation(sn.webg.archivage.service.annotations.FieldDisplayedFilter) && @annotation(fieldDisplayedFilter)")
    public Object abstractStorage(ProceedingJoinPoint p, FieldDisplayedFilter fieldDisplayedFilter) throws Throwable {

        /* Getting current user profile */
        RoleEntity role = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByUsername)
                .map(UserEntity::getRole)
                .orElseThrow(() -> new ForbiddenActionException(HttpStatus.FORBIDDEN));

        Object[] args = beforeProceed(p, role);


        /* Getting method output object */
        Object obj = p.proceed(args);

        return afterProceed(obj, fieldDisplayedFilter, role);

    }

    private Object[] beforeProceed(ProceedingJoinPoint p, RoleEntity role) {

        MethodSignature signature = (MethodSignature) p.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();

        Object[] args = p.getArgs();

        /* Deleting no metaData */
        for (int i = 0 ; i < parameters.length ; i++) {
            if (parameters[i].isAnnotationPresent(MetaDataSearchFilter.class)) {
                Map<String, Object> arg = (Map<String, Object>) args[i];
                ArchivageUtils.clearMataData(arg);
                break;
            }
        }

        /* no need to check the filter if it's an admin */
        if (role.getName().equals(SecurityCompositeRole.ADMIN)) {

            for (int i = 0 ; i < parameters.length ; i++) {
                if (parameters[i].isAnnotationPresent(IsAdmin.class)) {
                    args[i] = true;
                    break;
                }
            }
            return args;
        }

        for (int i = 0 ; i < parameters.length ; i++) {

            if (parameters[i].isAnnotationPresent(MetaDataSearchFilter.class)) {
                args[i] = validateMetaDataSearchFilter(role.getMetaDataList(), args[i]);
            } else if (parameters[i].isAnnotationPresent(MetaDataFilter.class)) {
                args[i] = validateMetaDataFilter(role.getMetaDataList(), args[i]);
            } else if (parameters[i].isAnnotationPresent(FolderTypeFilter.class)) {
                args[i] = validateFolderTypeFilter(role.getFolderTypes(), args[i]);
            } else if (parameters[i].isAnnotationPresent(FolderFilter.class)) {
                args[i] = validateFolderFilter(role.getFolders(), args[i]);
            } else if (parameters[i].isAnnotationPresent(DocumentTypeFilter.class)) {
                args[i] = validateDocumentTypeFilter(role.getDocumentTypes(), args[i]);
            } else if (parameters[i].isAnnotationPresent(DocumentFilter.class)) {
                args[i] = validateDocumentFilter(role.getDocuments(), args[i]);
            } else if (parameters[i].isAnnotationPresent(IsAdmin.class)) {
                args[i] = false;
            }
        }
        return args;

    }

    /**
     * Validate the search for the metadata it has access to
     * @param metaDataList all access MetaData
     * @param arg argument
     */
    private Object validateMetaDataSearchFilter(Set<MetaDataEntity> metaDataList, Object arg) {

        /* Checking if access metaData */
        if (Objects.isNull(metaDataList)) {
            return null;
        }

        if (Objects.nonNull(arg)) {

            /* Getting all access metaData */
            Set<String> metaDatas = metaDataList.stream().map(MetaDataEntity::getLabel).collect(Collectors.toSet());

            /* Getting all metadata that he does not have access to and that he put on the filter */
            Set<String> keyNotContent = ((Map<String, Object>) arg).keySet().stream().filter(key -> !metaDatas.contains(deletePrefixAndSuffixFromKey(key))).collect(Collectors.toSet());

            /* Deleting all metadata that he does not have access to and that he put on the filter */

            keyNotContent.forEach(key -> {
                String keyMatch = deletePrefixAndSuffixFromKey(key);
                if (!metaDatas.contains(keyMatch)) {
                    ((Map<String, Object>) arg).remove(key);
                }
            });

        }

        return arg;

    }


    /**
     * Validate the list of metadata it can search
     * @param metaDataList all access MetaData
     * @param arg argument
     */
    private Object validateMetaDataFilter(Set<MetaDataEntity> metaDataList, Object arg) {
        /* Checking if access metaData */
        if (Objects.isNull(metaDataList)) {
            return null;
        }

        /* Checking argument search metaData */
        if (Objects.isNull(arg)) {
            return metaDataList.stream().map(MetaDataEntity::getLabel).collect(Collectors.toSet());
        }

        /* Getting all metadata that he does not have access to and that he put on the filter */
        Set<String> metaDataNoAccess = ((Set<String>)arg).stream()
                .filter(metaData -> metaDataList.stream()
                        .noneMatch(metaDataEntity -> metaDataEntity.getId().equals(metaData) || metaDataEntity.getLabel().toLowerCase(Locale.ENGLISH).contains(metaData.toLowerCase(Locale.ENGLISH)))
                ).collect(Collectors.toSet());


        /* Deleting all metadata that he does not have access to and that he put on the filter */
        metaDataNoAccess.forEach(((Set<String>)arg)::remove);

        return arg;
    }

    private Object validateFolderFilter(Set<FolderEntity> folders, Object arg) {

        /* Checking if access folders */
        if (Objects.isNull(folders)) {
            return null;
        }

        /* Checking argument search folder */
        if (Objects.isNull(arg)) {
            return folders.stream().map(FolderEntity::getId).collect(Collectors.toSet());
        }

        /* Getting all folder that he does not have access to and that he put on the filter */
        Set<String> folderNoAccess = ((Set<String>)arg).stream()
                .filter(folder -> folders.stream()
                        .noneMatch(folderEntity -> folderEntity.getId().equals(folder) ||  folderEntity.getReference().equals(folder))
                ).collect(Collectors.toSet());


        /* Deleting all folder that he does not have access to and that he put on the filter */
        folderNoAccess.forEach(((Set<String>)arg)::remove);

        return arg;
    }

    private Object validateFolderTypeFilter(Set<FolderTypeEntity> folderTypes, Object arg) {

        /* Checking if access folderTypes */
        if (Objects.isNull(folderTypes)) {
            return null;
        }

        /* Checking argument search folderType */
        if (Objects.isNull(arg)) {
            return folderTypes.stream().map(FolderTypeEntity::getId).collect(Collectors.toSet());
        }

        /* Getting all folderType that he does not have access to and that he put on the filter */
        Set<String> folderTypeNoAccess = ((Set<String>)arg).stream()
                .filter(folderType -> folderTypes.stream()
                        .noneMatch(folderTypeEntity -> folderTypeEntity.getId().equals(folderType) || folderTypeEntity.getCode().equals(folderType))
                ).collect(Collectors.toSet());


        /* Deleting all folderType that he does not have access to and that he put on the filter */
        folderTypeNoAccess.forEach(((Set<String>)arg)::remove);

        return arg;
    }

    private Object validateDocumentFilter(Set<DocumentEntity> documents, Object arg) {

        /* Checking if access documents */
        if (Objects.isNull(documents)) {
            return null;
        }

        /* Checking argument search documents */
        if (Objects.isNull(arg)) {
            return documents.stream().map(DocumentEntity::getId).collect(Collectors.toSet());
        }

        /* Getting all documents that he does not have access to and that he put on the filter */
        Set<String> documentNoAccess = ((Set<String>)arg).stream()
                .filter(document -> documents.stream()
                        .noneMatch(documentEntity -> documentEntity.getId().equals(document) || documentEntity.getReference().equals(document))
                ).collect(Collectors.toSet());


        /* Deleting all documents that he does not have access to and that he put on the filter */
        documentNoAccess.forEach(((Set<String>)arg)::remove);

        return arg;
    }

    private Object validateDocumentTypeFilter(Set<DocumentTypeEntity> documentTypes, Object arg) {

        /* Checking if access documentTypes */
        if (Objects.isNull(documentTypes)) {
            return null;
        }

        /* Checking argument search documentType */
        if (Objects.isNull(arg)) {
            return documentTypes.stream().map(DocumentTypeEntity::getId).collect(Collectors.toSet());
        }

        /* Getting all documentType that he does not have access to and that he put on the filter */
        Set<String> documentTypeNoAccess = ((Set<String>)arg).stream()
                .filter(documentType -> documentTypes.stream()
                        .noneMatch(folderTypeEntity -> folderTypeEntity.getId().equals(documentType) || folderTypeEntity.getCode().equals(documentType))
                ).collect(Collectors.toSet());


        /* Deleting all documentType that he does not have access to and that he put on the filter */
        documentTypeNoAccess.forEach(((Set<String>)arg)::remove);

        return arg;
    }

    private Object afterProceed(Object obj, FieldDisplayedFilter fieldDisplayedFilter, RoleEntity role) {

        /* Return object if user profile match ADMIN */
        if (role.getName().equals(SecurityCompositeRole.ADMIN)) {
            return obj;
        }

        /* Checking if obeject type is pageable */
        if (fieldDisplayedFilter.pageable()) {

            if (!((Page<AbstractStorage>) obj).isEmpty()){

                /* Getting all metaData not displayed */
                Set<String> metaDataNotDisplayed = getMetaDataNotDisplayed(((Page<AbstractStorage>) obj).getContent().get(0), getMetaDataProfiles(role));

                /* Iterate and remove all field not displayed */
                return ((Page<AbstractStorage>) obj).map(abstractStorage -> {
                    metaDataNotDisplayed.forEach( metaData -> abstractStorage.getMetaDatas().remove(metaData));
                    return abstractStorage;
                });
            }
            return obj;

        } else {

            /* Getting all metaData not displayed */
            Set<String> metaDataNotDisplayed = getMetaDataNotDisplayed(((AbstractStorage) obj), getMetaDataProfiles(role));

            /* Remove field not displayed */
            metaDataNotDisplayed.forEach( metaData -> ((AbstractStorage) obj).getMetaDatas().remove(metaData));

            return ((AbstractStorage) obj).getMetaDatas();
        }

    }

    /**
     * Getting all metaData not displayed based metaData from profile
     * @param abstractStorage folder or Document
     * @param metaDataProfiles metaData from profile
     * @return Set<String>
     */
    private Set<String> getMetaDataNotDisplayed(AbstractStorage abstractStorage , Set<String> metaDataProfiles) {
        return abstractStorage.getMetaDatas().keySet()
                .stream()
                .filter(metaData -> !metaDataProfiles.contains(metaData))
                .collect(Collectors.toSet());
    }

    /**
     * Getting all metaData from profile
     * @param role profile current user
     * @return Set<String>
     */
    private Set<String> getMetaDataProfiles(RoleEntity role) {

        return role.getMetaDataList() != null ?
                role.getMetaDataList().stream().map(MetaDataEntity::getLabel).collect(Collectors.toSet()) : new HashSet<>();
    }

    private String deletePrefixAndSuffixFromKey(@NotNull String key) {
        return key.replaceAll(START, "").replaceAll(END, "");
    }

}
