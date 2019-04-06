package Interfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import Classes.Store;

public class IFolder {
    private MyDLinkedList folderList;
    private String folderName;

    public MyDLinkedList getFolderList() {
        return this.folderList;
    }

    public void setFolderList(MyDLinkedList folderList) {
        this.folderList = folderList;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public IFolder(String p) {
        this.folderName = p;
        this.creatfolder(this.folderName);
    }

    public IFolder() {
    }

    public void creatfolder(String p) {
        File dir = new File(p);
        boolean successful = dir.mkdir();
        if (successful) {
            System.out.println("directory was created successfully");
        } else {
            System.out.println("failed trying to create the directory");
        }

    }

    public void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        FileOutputStream os = null;

        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];

            int length;
            while((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }

    }

    public void moveTo(IMail email, String p) {
        Store s = new Store();
        s.storeMail(email, p);
    }

    public boolean deleteDirectory(String path) {
        File directoryToBeDeleted = new File(path);
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            File[] var4 = allContents;
            int var5 = allContents.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File file = var4[var6];
                this.deleteDirectory(file.getPath());
            }
        }

        return directoryToBeDeleted.delete();
    }

    public void copyFolder(File src, File dest) throws IOException {
        int length;
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }

            String[] files = src.list();
            String[] var4 = files;
            int var5 = files.length;

            for(length = 0; length < var5; ++length) {
                String file = var4[length];
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                this.copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];

            while((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
        }

    }

    public void rename(String oldName, String newName, String path) {
        File old = new File(path + "/" + oldName);
        File newN = new File(path + "/" + newName);

        try {
            this.copyFolder(old, newN);
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        this.deleteDirectory(path + "/" + oldName);
    }
}

