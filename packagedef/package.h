/*
 * @author geno1024
 * @date 2024-07-28 10:08:20
 */

#ifndef NEWHOME_PACKAGE_H
#define NEWHOME_PACKAGE_H

#include <stdint-gcc.h>

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
    int32_t metadataSize;
    NHPackageMetadata *metadata;
    int32_t fileEntryLength;
    NHPackageFileEntry *fileEntries;
} NHPackage;

#endif //NEWHOME_PACKAGE_H
