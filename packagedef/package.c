/*
 * @author geno1024
 * @date 2024-07-28 10:08:19
 */

#include <stdlib.h>
#include "package.h"

NHPackage *NHPackageNew$1()
{
    NHPackage *package = malloc(sizeof(NHPackage));
    package->magic[0] = 'G';
    package->magic[1] = 'M';
    package->magic[2] = 'N';
    package->magic[3] = 'H';
    package->majorVersion[0] = 0;
    package->majorVersion[1] = 1;
    package->minorVersion[0] = 0;
    package->minorVersion[1] = 1;
    return package;
}

void NHPackageDelete$1(NHPackage *package)
{
    free(package);
}

void NHPackageSave$1(NHPackage *package, FILE *file)
{
    fwrite(package->magic, sizeof(char), 4, file);
    fwrite(package->majorVersion, sizeof(char), 2, file);
    fwrite(package->minorVersion, sizeof(char), 2, file);
    fwrite(package->timestamp, sizeof(char), 4, file);
    fwrite(&package->packageNameLength, sizeof(int32_t), 1, file);
    fwrite(package->packageName, sizeof(char), package->packageNameLength, file);
    fwrite(&package->metadataCount, sizeof(int32_t), 1, file);
    for (int i = 0; i < package->metadataCount; i++)
    {
        NHPackageMetadata *thiz = package->metadata[i];
        fwrite(&thiz->keyLength, sizeof(int32_t), 1, file);
        fwrite(thiz->key, sizeof(char), thiz->keyLength, file);
        fwrite(&thiz->valueLength, sizeof(int32_t), 1, file);
        fwrite(thiz->value, sizeof(char), thiz->valueLength, file);
    }
    fwrite(&package->fileEntryCount, sizeof(int32_t), 1, file);
    for (int i = 0; i < package->fileEntryCount; i++)
    {
        NHPackageFileEntry *thiz = package->fileEntries[i];
        fwrite(&thiz->fileNameLength, sizeof(int32_t), 1, file);
        fwrite(thiz->fileName, sizeof(char), thiz->fileNameLength, file);
        fwrite(&thiz->compressionMethod, sizeof(int32_t), 1, file);
        fwrite(&thiz->compressedSize, sizeof(int32_t), 1, file);
        fwrite(&thiz->uncompressedSize, sizeof(int32_t), 1, file);
        fwrite(thiz->compressedBody, sizeof(char), thiz->compressedSize, file);

    }
}

int main(int argc, char *argv[])
{
    NHPackage *p = NHPackageNew$1();
    FILE *fp = fopen("a.gmnp", "wb+");
    NHPackageSave$1(p, fp);
    fclose(fp);
    NHPackageDelete$1(p);
    return 0;
}
