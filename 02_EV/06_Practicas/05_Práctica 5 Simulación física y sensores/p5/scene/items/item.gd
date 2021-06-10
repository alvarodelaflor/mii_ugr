extends Spatial
class_name Weapon

# References
var item_manager = null
var player = null
var ray = null

# Weapon States
var is_equipped = false

# Weapon Parameters
export var item_name = "Weapon"
export(Texture) var weapon_image = null


# Equip/Unequip Cycle
func equip():
	pass

func unequip():
	pass

func is_equip_finished():
	return true

func is_unequip_finished():
	return true



# Update Ammo
func update_ammo(action = "Refresh"):
	
	var weapon_data = {
		"Name" : item_name,
		"Image" : weapon_image
	}
	
	item_manager.update_hud(weapon_data)
