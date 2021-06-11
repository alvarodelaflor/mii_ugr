extends Item
class_name Armed

# Rigidbody Version
export(PackedScene) var item_pickup

# References
var animation_player

# Item Parameters
export var wear_in_mag = 15
export var maximum_wear = 30
onready var mag_size = wear_in_mag

# The offset of the item from the camera
export var equip_pos = Vector3.ZERO

# Effects
export(PackedScene) var impact_effect
export(NodePath) var muzzle_flash_path
onready var muzzle_flash = get_node(muzzle_flash_path)

# Optional
export var equip_speed = 1.0
export var unequip_speed = 1.0

# Equip/Unequip Cycle
func equip():
	animation_player.play("Equip", -1.0, equip_speed)

func unequip():
	animation_player.play("Unequip", -1.0, unequip_speed)

func is_equip_finished():
	if is_equipped:
		return true
	else:
		return false

func is_unequip_finished():
	if is_equipped:
		return false
	else:
		return true

# Show/Hide Item
func show_item():
	visible = true

func hide_item():
	visible = false

# Animation Finished
func on_animation_finish(anim_name):
	match anim_name:
		"Unequip":
			is_equipped = false
		"Equip":
			is_equipped = true

# Update Wear
func update_item(action = "Refresh", additional_wear = 0):
	
	var item_data = {
		"Name" : item_name,
		"Image" : item_image,
		"Wear" : str(wear_in_mag),
		"MaximumWear" : str(maximum_wear)
	}
	
	item_manager.update_hud(item_data)


# Drops item on the ground, by instancing item_pickup, and removing itself from the tree
func drop_item():
	var pickup = Global.instantiate_node(item_pickup, global_transform.origin - player.global_transform.basis.z.normalized())
	pickup.wear_in_mag = wear_in_mag
	pickup.maximum_wear = maximum_wear
	pickup.mag_size = mag_size
	
	queue_free()







