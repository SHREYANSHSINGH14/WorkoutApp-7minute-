package com.example.a7minworkoutapp

class ExerciseModel(
    private var id:Int,
    private var name:String,
    private var image:Int,
    private var isCompleted:Boolean,
    private var isSelected:Boolean
){
    fun getId():Int{
        return this.id
    }
    fun setId(id:Int){
        this.id = id
    }
    fun getName():String{
        return this.name
    }
    fun setName(name: String){
        this.name = name
    }
    fun getImg():Int{
        return this.image
    }
    fun setImg(image: Int){
        this.image = image
    }
    fun getIsCompleted():Boolean{
        return this.isCompleted
    }
    fun setIsCompleted(isCompleted: Boolean){
        this.isCompleted = isCompleted
    }
    fun getIsSelected():Boolean{
        return this.isSelected
    }
    fun setIsSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }
}