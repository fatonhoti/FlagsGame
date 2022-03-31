-keep class com.blongho.** {*;}
-keep interface com.blongho.**
# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keeppackagenames com.blongho.country_data
-keepclassmembers class com.blongho.country_data.* {
   public *;
}
-keep class com.blongho.country_data.R$*{
    *;
 }