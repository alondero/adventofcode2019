package adventofcode2019.day8

class SpaceImage(val layers: List<Layer>) {
    override fun toString() = layers.toString()
}

class Layer(val rows: List<String>) {
    override fun toString() = rows.toString()
}

fun fromSIF(sif: String, width: Int, height: Int) =
    SpaceImage(sif.chunked(width * height)
        .map { it.chunked(width) }
        .map { Layer(it) })

