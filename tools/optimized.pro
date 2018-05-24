
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Remove logs
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Heavy Obfuscation (Slow, but makes it very hard to reverse engineer)
-allowaccessmodification
-repackageclasses ''
-flattenpackagehierarchy

# Preserve native methods, class names, and signatures:
-keepclasseswithmembernames,includedescriptorclasses class * { 
    native <methods>; 
}