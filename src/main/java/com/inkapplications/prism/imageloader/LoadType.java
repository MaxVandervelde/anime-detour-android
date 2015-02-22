package com.inkapplications.prism.imageloader;

public enum  LoadType
{
    NETWORK,
    DISK,
    MEMORY,
    UNKNOWN;

    public boolean isCache() {
        return this == DISK || this == MEMORY;
    }
}
