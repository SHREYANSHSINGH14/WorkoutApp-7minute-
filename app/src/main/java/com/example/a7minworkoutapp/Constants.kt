package com.example.a7minworkoutapp

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingJacks)
        val wallSit = ExerciseModel(
            2,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false
        )
        exerciseList.add(wallSit)
        val pushUp = ExerciseModel(
            3,
            "Push Up",
            R.drawable.ic_push_up,
            false,
            false
        )
        exerciseList.add(pushUp)
        val crunches = ExerciseModel(
            4,
            "Crunches",
            R.drawable.ic_abdominal_crunch,
            false,
            false
        )
        exerciseList.add(crunches)
        val stepUpOnChair = ExerciseModel(
            5,
            "Step Up On Chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false
        )
        exerciseList.add(stepUpOnChair)
        val squats = ExerciseModel(
            6,
            "Squats",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squats)
        val tricepDips = ExerciseModel(
            7,
            "Tricep Dips",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false
        )
        exerciseList.add(tricepDips)
        val plank = ExerciseModel(
            8,
            "Plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)
        val highKnees = ExerciseModel(
            9,
            "High Knees",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )
        exerciseList.add(highKnees)
        val lunges = ExerciseModel(
            10,
            "Lunges",
            R.drawable.ic_lunge,
            false,
            false
        )
        exerciseList.add(lunges)
        val pushUpAndRotation = ExerciseModel(
            11,
            "Push Up and Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false
        )
        exerciseList.add(pushUpAndRotation)
        val sidePlank = ExerciseModel(
            12,
            "Side Plank",
            R.drawable.ic_side_plank,
            false,
            false
        )
        exerciseList.add(sidePlank)
        return  exerciseList
    }
}