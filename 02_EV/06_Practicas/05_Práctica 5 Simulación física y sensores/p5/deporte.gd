extends RigidBody

export var weapon_name = "Weapon"
export var ammo_in_mag = 5
export var extra_ammo = 10
onready var mag_size = ammo_in_mag
export(PackedScene) var weapon_pickup

func _ready():
	connect("sleeping_state_changed", self, "on_sleeping")
	
func get_weapon_pickup_data():
	return {
		"Name": weapon_name,
		"Ammo": ammo_in_mag,
		"ExtraAmmo": extra_ammo,
		"MagSize": mag_size
	}
	
func on_sleeping():
	mode = MODE_STATIC
	
func drop_weapon():
	var pickup = Global.instance_node()
	
