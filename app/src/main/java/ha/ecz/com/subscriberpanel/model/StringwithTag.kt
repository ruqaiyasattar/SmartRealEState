package ha.ecz.com.subscriberpanel.model

class StringwithTag(stringPart:String, tagPart:Int) {
    var name:String = stringPart
    var tag:Int = tagPart
    override fun toString():String {
        return name
    }
}