extends Item
class_name Armed

# Rigidbody Version
export(PackedScene) var item_pickup

# References
var animation_player

# Item Parameters
export var ammo_in_mag = 15
export var extra_ammo = 30
onready var mag_size = ammo_in_mag

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

# Update Ammo
func update_item(action = "Refresh", additional_ammo = 0):
	
	var item_data = {
		"Name" : item_name,
		"Image" : item_image,
		"Ammo" : str(ammo_in_mag),
		"ExtraAmmo" : str(extra_ammo)
	}
	
	item_manager.update_hud(item_data)


# Drops item on the ground, by instancing item_pickup, and removing itself from the tree
func drop_item():
	var pickup = Global.instantiate_node(item_pickup, global_transform.origin - player.global_transform.basis.z.normalized())
	pickup.ammo_in_mag = ammo_in_mag
	pickup.extra_ammo = extra_ammo
	pickup.mag_size = mag_size
	
	queue_free()







