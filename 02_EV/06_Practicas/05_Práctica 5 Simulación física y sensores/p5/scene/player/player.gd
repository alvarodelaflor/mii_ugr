extends KinematicBody

const MOUSE_SENSITIVITY = 0.1

# Variables fisicas
const GRAVITY = -40.0
const JUMP_SPEED = 15
var jump_counter = 0 # Utilizamos esta variable para evitar que el jugador salte indefinidamente
const SPEED = 10
const SPRINT_SPEED = 20
# Utilizamos las siguientes dos variables para suavizar el movimiento
const AIR_ACCEL = 9.0
const ACCEL = 15.0

# Incicializamos los vectores
var velocity = Vector3.ZERO
var current_vel = Vector3.ZERO
var dir = Vector3.ZERO

# Configuramos las camaras
onready var camera = $CamRoot/Camera
onready var item_manager = $CamRoot/Items

# Ocultamos inicialmente el cursor
func _ready():
	Input.set_mouse_mode(Input.MOUSE_MODE_CAPTURED)

func _process(delta):
	# Si el usuario lo desea puede activar el cursor pulsando en la tecla ESC
	window_activity()

func window_activity():
	if Input.is_action_just_pressed("ui_cancel"):
		if Input.get_mouse_mode() == Input.MOUSE_MODE_CAPTURED:
			Input.set_mouse_mode(Input.MOUSE_MODE_VISIBLE)
		else:
			Input.set_mouse_mode(Input.MOUSE_MODE_CAPTURED)

func _physics_process(delta):
	# Capturamos las ordenes de desplazamiento con las teclas y permitimos movimiento
	process_movement_inputs()
	process_movement(delta)
	# Capturamos la orden de salto
	process_jump(delta)
	# Modificar inventario
	process_items()

func process_movement_inputs():
	dir = Vector3.ZERO
	
	if Input.is_action_pressed("forward"):
		dir -= camera.global_transform.basis.z
	if Input.is_action_pressed("backward"):
		dir += camera.global_transform.basis.z
	if Input.is_action_pressed("right"):
		dir += camera.global_transform.basis.x
	if Input.is_action_pressed("left"):
		dir -= camera.global_transform.basis.x
	
	# Normalizamos
	dir = dir.normalized()
	
func process_movement(delta):
	# Configuramos la velocidad y permitimos correr
	var speed = SPRINT_SPEED if Input.is_action_pressed("sprint") else SPEED
	var target_vel = dir * speed
	
	# Suavizamos el movimiento
	var accel = ACCEL if is_on_floor() else AIR_ACCEL
	current_vel = current_vel.linear_interpolate(target_vel, accel * delta)
	
	velocity.x = current_vel.x
	velocity.z = current_vel.z
	
	velocity = move_and_slide(velocity, Vector3.UP, true, 4, deg2rad(45))

func process_jump(delta):
	# Applimos la gravedad
	velocity.y += GRAVITY * delta
	
	if is_on_floor():
		jump_counter = 0
	
	# Permitimos saltar si no ha saltado antes
	if Input.is_action_just_pressed("jump") and jump_counter < 2:
		jump_counter += 1
		velocity.y = JUMP_SPEED


func process_items():
	if Input.is_action_just_pressed("empty"):
		item_manager.change_item("Empty")
	if Input.is_action_just_pressed("primary"):
		item_manager.change_item("Primary")
	if Input.is_action_just_pressed("secondary"):
		item_manager.change_item("Secondary")
	
	# Tirar Item
	if Input.is_action_just_pressed("drop"):
		item_manager.drop_item()
	
	# Coger item
	item_manager.process_item_pickup()



func _input(event):
	if event is InputEventMouseMotion:
		# Rotates the view vertically
		$CamRoot.rotate_x(deg2rad(event.relative.y * MOUSE_SENSITIVITY * -1))
		$CamRoot.rotation_degrees.x = clamp($CamRoot.rotation_degrees.x, -75, 75)
		
		# Rotates the view horizontally
		self.rotate_y(deg2rad(event.relative.x * MOUSE_SENSITIVITY * -1))
	
	if event is InputEventMouseButton:
		if event.pressed:
			match event.button_index:
				BUTTON_WHEEL_UP:
					item_manager.next_item()
				BUTTON_WHEEL_DOWN:
					item_manager.previous_item()

func _on_Spatial_camara_2():
	$CamRoot/Camera.current=true
