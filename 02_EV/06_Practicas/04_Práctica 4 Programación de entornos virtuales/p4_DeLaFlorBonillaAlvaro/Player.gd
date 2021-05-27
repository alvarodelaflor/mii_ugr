extends KinematicBody

# Variables físicas
var moveSpeed : float = 6.0
var jumpForce : float = 3.2
var gravity : float = 9.8

# Variables de la cámara
var minLookAngle : float = -25.0
var maxLookAngle : float = 25.0
export var lookSensitivity : float = 10.0 setget change_sensitivity

func change_sensitivity(new_sensitivity):
	lookSensitivity = new_sensitivity

# Inicializamos vectores
var vel : Vector3 = Vector3()
var mouseDelta : Vector2 = Vector2()

# Preparamos la cámara
onready var camera : Camera = get_node("Camera")

###########################################################
## PARTE 1  - MOVIMIENTO DE LA CAMARA CON TECLAS Y SALTO ##
###########################################################
func _physics_process(delta):
	
	# Reseteamos velocidad
	vel.x = 0
	vel.z = 0
	
	var input = Vector2()
	
	# Capturamos pulsación de las teclas
	if Input.is_action_pressed("w"):
		input.y -= 1
	if Input.is_action_pressed("s"):
		input.y += 1
	if Input.is_action_pressed("a"):	
		input.x -= 1
	if Input.is_action_pressed("d"):
		input.x += 1
		
	input = input.normalized()
	
	# Obtenemos las direcciones hacia adelante y hacia la derecha
	var forward = global_transform.basis.z
	var right  = global_transform.basis.x
	
	var relativeDir = (forward * input.y + right * input.x)
	
	# Aplicamos la nueva velocidad
	vel.x = relativeDir.x * moveSpeed
	vel.z = relativeDir.z * moveSpeed
	
	# Le aplicamos la fuerza de gravedad
	vel.y -= gravity * delta
	
	# Finalmente movemos al usuario
	vel = move_and_slide(vel, Vector3.UP)
	
	# Saltamos
	if Input.is_action_pressed("space"):
		vel.y = jumpForce
###########################################################
## PARTE 1  - MOVIMIENTO DE LA CAMARA CON TECLAS Y SALTO ##
###########################################################

###########################################################
##### PARTE 2  - MOVIMIENTO DE LA CAMARA CON EL RATÓN #####
###########################################################
func _ready():
	# Ocultamos el cursor al iniciar
	Input.set_mouse_mode(Input.MOUSE_MODE_CAPTURED)

func _process(delta):
	
	# rotate the camera along the x axis
	camera.rotation_degrees.x -= mouseDelta.y * lookSensitivity * delta
	
	# clamp camera x rotation axixs
	camera.rotation_degrees.x = clamp(camera.rotation_degrees.x, minLookAngle, maxLookAngle)
	
	# rotate the player along their y axixs
	rotation_degrees.y -= mouseDelta.x * lookSensitivity * delta
	
	# reset the mouse delta vector
	mouseDelta = Vector2()
	
func _input(event):
	
	if event is InputEventMouseMotion:
		mouseDelta = event.relative
###########################################################
##### PARTE 2  - MOVIMIENTO DE LA CAMARA CON EL RATÓN #####
###########################################################


func _on_Spatial_camara_2():
	get_node("Camera").current=true
