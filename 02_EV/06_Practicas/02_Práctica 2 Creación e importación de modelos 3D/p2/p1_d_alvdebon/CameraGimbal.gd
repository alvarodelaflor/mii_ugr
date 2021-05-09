extends Spatial

export var rotation_speed = PI / 2
var y_rotation_status = 0
var x_rotation_status = 0
var max_move = 25

func _process(delta):
	get_input_keyboard(delta)
	
func get_input_keyboard(delta):
	var y_rotation = 0
	if (Input.is_action_pressed("derecha") && y_rotation_status <= max_move):
		y_rotation += 1
		y_rotation_status +=1
	if (Input.is_action_pressed("izquierda") && y_rotation_status >= -max_move):
		y_rotation -= 1
		y_rotation_status -= 1
	rotate_object_local(Vector3.UP, y_rotation * rotation_speed * delta)
	var x_rotation = 0
	if (Input.is_action_pressed("arriba") && x_rotation_status <= max_move):
		x_rotation += 1
		x_rotation_status += 1
	if (Input.is_action_pressed("abajo") && x_rotation_status >= -max_move):
		x_rotation -=1
		x_rotation_status -= 1
	$InnerGimbal.rotate(Vector3.RIGHT, x_rotation * rotation_speed * delta)
