extends Spatial

export var rotation_speed = PI / 2
var y_rotation_status = 0
var x_rotation_status = 0
var max_move = 25

func _process(delta):
	get_input_keyboard(delta)
	
func get_input_keyboard(delta):
	var y_rotation = 0
	if (Input.is_action_pressed("derecha")):
		get_node("Armature/AnimationPlayer").play("Walk")
