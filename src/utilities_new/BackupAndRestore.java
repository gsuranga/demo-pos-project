/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities_new;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author shdinesh.99
 */
public class BackupAndRestore {

    public static void backupDB() {
        String backupDir = getBackupDir();
        File path = new File(backupDir + "dimo_backups");
        String drive = backupDir.replace("\\", "").replace(":", "");
        if (!path.exists()) {
            path.mkdir();
        }
//        path.setExecutable(false);
//        path.setReadable(false);
//        path.setWritable(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        sdf.applyPattern("HH:mm:ss");
        String time = sdf.format(new Date());
        String newTime = time.replaceAll(":", "-");
        String fileName = "\\dimo_backup_" + date + newTime + ".sql";
        Process p = null;
        try {

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec("mysqldump -uroot -p123 --add-drop-database -B demolanka -r " + path + fileName);
            runtime.exec("cmd.exe /d  start cacls dimo_backups /e /d %shdinesh.99%");
            int processComplete = p.waitFor();
            if (processComplete > 0) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RestoreDB() {

    }

    public static String getBackupDir() {
        File[] drives = File.listRoots();
        String driveName = "";
        for (int i = 0; i < drives.length; i++) {
            driveName = drives[i].toString();
            boolean exsists = drives[i].exists();
            if (!"C:\\".equals(driveName) && exsists == true) {
                break;
            }
        }
        return driveName;

    }

//    public static void main(String[] args) {
//        backupDB();
//    }
}
