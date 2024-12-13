import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    println("Enter the folder path containing JPEG files:")
    val folderPath = readlnOrNull()

    if (folderPath.isNullOrBlank()) {
        println("Invalid folder path.")
        return
    }

    val folder = File(folderPath)

    if (!folder.exists() || !folder.isDirectory) {
        println("The specified path is not a valid folder.")
        return
    }

    val jpegFiles = folder.listFiles { file ->
        file.isFile && (file.extension.equals("jpeg", true)
                || file.extension.equals("jpg", true))
    }

    if (jpegFiles.isNullOrEmpty()) {
        println("No JPEG files found in the folder.")
        return
    }

    jpegFiles.forEach { jpegFile ->
        try {
            val inputImage: BufferedImage = ImageIO.read(jpegFile)
            val outputFileName = jpegFile.nameWithoutExtension + ".png"
            val outputFile = File(folder, outputFileName)

            if (ImageIO.write(inputImage, "png", outputFile)) {
                println("Converted: ${jpegFile.name} -> ${outputFile.name}")
            } else {
                println("Failed to convert: ${jpegFile.name}")
            }
        } catch (e: Exception) {
            println("Error processing file ${jpegFile.name}: ${e.message}")
        }
    }

    println("Conversion process completed.")
}
