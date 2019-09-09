package ha.ecz.com.subscriberpanel.Extras

fun String?.emptyOrDouble() = if(this != null && (this.length) > 0) this.toDouble() else 0.0


fun Int?.emptyOradd() = if(this == null) 0 else this.toInt()