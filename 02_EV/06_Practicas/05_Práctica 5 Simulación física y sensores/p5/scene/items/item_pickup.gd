extends RigidBody


# Item parameters that will be transferred if this item is picked
export var item_name = "Item"
export var wear_in_item = 5
export var maximum_wear = 10
onready var mag_size = wear_in_item


func _ready():
	connect("sleeping_state_changed", self, "on_sleeping")



func get_item_pickup_data():
	return{
		"Name" : item_name,
		"Wear" : wear_in_item,
		"MaximumWear" : maximum_wear,
		"MagSize" : mag_size
	}



# When the rigidbody goes to sleeping state after being idle for sometime, it will be made static
func on_sleeping():
	mode = MODE_STATIC
