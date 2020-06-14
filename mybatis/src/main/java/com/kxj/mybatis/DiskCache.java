package com.kxj.mybatis;

import com.kxj.mybatis.common.EncryptUtil;
import org.apache.ibatis.cache.Cache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReadWriteLock;

// 硬盘存储，不丢失
public class DiskCache implements Cache {

    private final String id;
    private String cachePath;

    public DiskCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try {
            String keyMd5 = EncryptUtil.MD5(toBytes(key));
            Files.write(Paths.get(cachePath + "/" + keyMd5), toBytes(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] toBytes(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(obj);
        stream.close();
        return out.toByteArray();
    }

    @Override
    public Object getObject(Object key) {
        try {
            String keyMd5 = EncryptUtil.MD5(toBytes(key));
            FileInputStream inputStream = new FileInputStream(cachePath + "/" + keyMd5);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object o = objectInputStream.readObject();
            inputStream.close();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object removeObject(Object key) {
        try {
            String keyMd5 = EncryptUtil.MD5(toBytes(key));
            File f = new File(cachePath + "/" + keyMd5);
            if (f.exists()) {
                f.delete();
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return new File(cachePath).list().length;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }


    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }
}