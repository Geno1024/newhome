/*
 * @author geno1024
 * @date 2024-07-28 10:08:20
 */

#ifndef NEWHOME_PACKAGE_H
#define NEWHOME_PACKAGE_H

#include <stdint.h>
#include <stdio.h>

typedef enum NHCompressionMethod
{
    Plain,
    Deflate,
    Gzip
} NHCompressionMethod;

typedef struct NHPackageMetadata
{
    int32_t keyLength;
    char *key;
    int32_t valueLength;
    char *value;
} NHPackageMetadata;

typedef struct NHPackageFileEntry
{
    int32_t fileNameLength;
    char *fileName;
    NHCompressionMethod compressionMethod;
    int32_t compressedSize;
    int32_t uncompressedSize;
    char *compressedBody;
} NHPackageFileEntry;

typedef struct NHPackage
{
    char magic[4];
    char majorVersion[2];
    char minorVersion[2];
    char timestamp[4];
    int32_t packageNameLength;
    char *packageName;
    int32_t metadataCount;
    NHPackageMetadata **metadata;
    int32_t fileEntryCount;
    NHPackageFileEntry **fileEntries;
} NHPackage;

NHPackage *NHPackageNew$1();

void NHPackageDelete$1(NHPackage *package);

void NHPackageSave$1(NHPackage *package, FILE *file);

#endif //NEWHOME_PACKAGE_H
