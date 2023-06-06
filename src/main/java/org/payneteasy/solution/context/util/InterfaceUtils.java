package org.payneteasy.solution.context.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class InterfaceUtils {

    /**
     * Get all implementations for interface
     * @param superClass Interface class
     * @return List with unique classes casted to interface
     */
    @SuppressWarnings("unchecked")
    public static <T> List<Class<T>> getSubclasses(Class<T> superClass) {
        Set<Class<T>> subclasses = new HashSet<>();
        Package[] packages = superClass.getClassLoader().getDefinedPackages();

        for (Package pkg : packages) {
            String packageName = pkg.getName();
            Set<Class<?>> classes = getClasses(packageName);

            for (Class<?> clazz : classes) {
                if (superClass.isAssignableFrom(clazz) && !clazz.isInterface()) {
                    subclasses.add((Class<T>) clazz);
                }
            }
        }

        return new ArrayList<>(subclasses);
    }

    private static Set<Class<?>> getClasses(String packageName) {
        Set<Class<?>> classes = new HashSet<>();

        try {
            String path = packageName.replace('.', '/');
            URL resource = Thread.currentThread().getContextClassLoader().getResource(path);

            if (Objects.isNull(resource)) {
                return Collections.emptySet();
            }

            File file = new File(resource.getFile());

            if (file.isDirectory()) {
                processDirectory(packageName, file, classes);
            }

        } catch (ClassNotFoundException  | IOException e) {
            // ignored
        }

        return classes;
    }

    private static void processDirectory(String packageName, File directory, Set<Class<?>> classes)
            throws ClassNotFoundException, IOException {
        File[] files = directory.listFiles();

        if (Objects.isNull(files)) {
            return;
        }

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().lastIndexOf('.'));
                Class<?> clazz = Class.forName(className);
                classes.add(clazz);
            } else if (file.isDirectory()) {
                String subPackageName = packageName + '.' + file.getName();
                processDirectory(subPackageName, file, classes);
            }
        }
    }


//    private static List<Class<?>> getClasses(String packageName) {
//        try {
//            String path = packageName.replace('.', '/');
//            URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
//            if (Objects.isNull(resource)) {
//                return Collections.emptyList();
//            }
//            File file = new File(resource.getFile());
//
//            List<Class<?>> classes = new ArrayList<>();
//
//            if (file.isDirectory()) {
//                File[] files = file.listFiles();
//
//                if (Objects.isNull(files) || files.length == 0) {
//                    return Collections.emptyList();
//                }
//
//                for (File f : files) {
//                    if (f.isFile() && f.getName().endsWith(".class")) {
//                        String className = packageName + '.' + f.getName().substring(0, f.getName().lastIndexOf('.'));
//                        Class<?> clazz = Class.forName(className);
//                        classes.add(clazz);
//                    }
//                }
//            }
//            return classes;
//
//        } catch (ClassNotFoundException | NullPointerException e) {
//            //ignored
//        }
//        return Collections.emptyList();
//    }

}
