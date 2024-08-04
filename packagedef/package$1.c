/*
 * @author geno1024
 * @date 2024-08-04 18:18:13
 */
#include "package$1.h"
#include <stdlib.h>
#include <string.h>
#include <time.h>

NHPackage$1 *NHPackage$1New()
{
    NHPackage$1 *package = malloc(sizeof(NHPackage$1));
    package->magic = 0x484e4d47;
    package->majorVersion = 1;
    package->minorVersion = 0;
    package->timestamp = time(NULL);
    package->packageNameLength = 0;
    package->packageName = "";
    package->metadataCount = 0;
    package->metadata = malloc(16 * sizeof(NHPackage$1Metadata *));
    package->fileEntryCount = 0;
    package->fileEntries = malloc(16 * sizeof(NHPackage$1FileEntry *));
    return package;
}

void NHPackage$1Delete(NHPackage$1 *package)
{
    free(package);
}

NHPackage$1 *NHPackage$1SetTimestamp(NHPackage$1 *package, int64_t timestamp)
{
    package->timestamp = timestamp;
    return package;
}

NHPackage$1 *NHPackage$1SetPackageName(NHPackage$1 *package, char *name)
{
    package->packageNameLength = (int32_t) strlen(name);
    package->packageName = strdup(name);
    return package;
}

NHPackage$1 *NHPackage$1PutMetadata(NHPackage$1 *package, char *key, char *value)
{
    int find = NHPackage$1ExistsMetadata(package, key);
    if (find != -1)
    {
        package->metadata[find]->valueLength = (int32_t) strlen(value);
        package->metadata[find]->value = strdup(value);
        return package;
    }
    else
    {
        package->metadataCount += 1;
        NHPackage$1Metadata *newMetadata = malloc(sizeof(NHPackage$1Metadata));
        newMetadata->keyLength = (int32_t) strlen(key);
        newMetadata->key = strdup(key);
        newMetadata->valueLength = (int32_t) strlen(value);
        newMetadata->value = strdup(value);

        // TODO
    }
}

NHPackage$1Metadata *NHPackage$1GetMetadata(NHPackage$1 *package, char *key)
{
    for (int i = 0; i < package->metadataCount; ++i)
    {
        NHPackage$1Metadata *data = package->metadata[i];
        if (strcmp(data->key, key) == 0)
        {
            return data;
        }
    }
    return NULL;
}

int NHPackage$1ExistsMetadata(NHPackage$1 *package, char *key)
{
    for (int i = 0; i < package->metadataCount; ++i)
    {
        NHPackage$1Metadata *data = package->metadata[i];
        if (strcmp(data->key, key) == 0)
        {
            return i;
        }
    }
    return -1;
}

NHPackage$1 *NHPackage$1DeleteMetadata(NHPackage$1 *package, char *key)
{
    // TODO
}

void NHPackage$1Save(NHPackage$1 *package, FILE *file)
{
    fwrite(&package->magic, sizeof(int32_t), 1, file);
    fwrite(&package->majorVersion, sizeof(int16_t), 1, file);
    fwrite(&package->minorVersion, sizeof(int16_t), 1, file);
    fwrite(&package->timestamp, sizeof(int64_t), 1, file);
    fwrite(&package->packageNameLength, sizeof(int64_t), 1, file);
    fwrite(package->packageName, sizeof(char), strlen(package->packageName), file);
    fwrite(&package->metadataCount, sizeof(int32_t), 1, file);
    for (int i = 0; i < package->metadataCount; i++)
    {
        NHPackage$1Metadata *thiz = package->metadata[i];
        fwrite(&thiz->keyLength, sizeof(int32_t), 1, file);
        fwrite(thiz->key, sizeof(char), thiz->keyLength, file);
        fwrite(&thiz->valueLength, sizeof(int32_t), 1, file);
        fwrite(thiz->value, sizeof(char), thiz->valueLength, file);
    }
    fwrite(&package->fileEntryCount, sizeof(int32_t), 1, file);
    for (int i = 0; i < package->fileEntryCount; i++)
    {
        NHPackage$1FileEntry *thiz = package->fileEntries[i];
        fwrite(&thiz->fileNameLength, sizeof(int32_t), 1, file);
        fwrite(thiz->fileName, sizeof(char), thiz->fileNameLength, file);
        fwrite(&thiz->compressionMethod, sizeof(int32_t), 1, file);
        fwrite(&thiz->compressedSize, sizeof(int32_t), 1, file);
        fwrite(&thiz->uncompressedSize, sizeof(int32_t), 1, file);
        fwrite(thiz->compressedBody, sizeof(char), thiz->compressedSize, file);
    }
}