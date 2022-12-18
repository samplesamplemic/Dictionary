package com.example.vocabulary2.module

import com.google.gson.annotations.SerializedName

//model of the json obj response
class VocabularyModel {

    @SerializedName("word")
    var text: String? = null;
    var meaningWork: String? = null;
    var phonetics: List<Phonetics>? = null
    var meanings: List<Meanings>? = null;
}

class Phonetics() {
    var text: String? = null;
    var audio: String? = null;
}

class Meanings() {
    var partOfSpeech: String? = null;
    var definitions: List<Definition>? = null;
}

class Definition {
    var definition: String? = null;
    var example: String? = null;
}
