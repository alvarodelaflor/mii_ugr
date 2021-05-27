extends Spatial

func _process(_delta):
	get_input_keyboard()
	
func get_input_keyboard():
	if (Input.is_action_pressed("l")):
		get_node("AnimationPlayer").play("light")
	elif (Input.is_action_pressed("c")):
		get_node("AnimationPlayer2").play("camara")
	elif (Input.is_action_pressed("r")):
		get_node("AnimationPlayer3").play("run")
