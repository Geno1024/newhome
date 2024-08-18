/*
 * @author geno1024
 * @date 2024-08-04 17:10:42
 */
#ifndef NEWHOME_PACKAGE_H
#define NEWHOME_PACKAGE_H

#include "package$1.h"

#define VER $1

#define __NHPackage(V) NHPackage##V
#define _NHPackage(V) __NHPackage(V)
#define NHPackage _NHPackage(VER)

#define __NHPackageF(V, Postfix) NHPackage##V##Postfix
#define _NHPackageF(V, Postfix) __NHPackageF(V, Postfix)
#define NHPackageF(Postfix) _NHPackageF(VER, Postfix)

#define NHPackageNew NHPackageF(New)

#endif // NEWHOME_PACKAGE_H
