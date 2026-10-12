cd "$(dirname "$0")"
java --module-path lib --add-modules javafx.controls --enable-native-access=javafx.graphics \
    -cp ".:lib/jimObjModelImporterJFX.jar" Main
