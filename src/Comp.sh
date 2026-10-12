cd "$(dirname "$0")"
javac --module-path lib --add-modules javafx.controls,javafx.fxml \
    -cp ".:lib/jimObjModelImporterJFX.jar" Main.java
