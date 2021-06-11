extends Spatial
class_name Item

# References
var item_manager = null
var player = null
var ray = null

# Item States
var is_equipped = false

# Item Parameters
export var item_name = "Item"
export(Texture) var item_image = null


# Equip/Unequip Cycle
func equip():
	pass

func unequip():
	pass

func is_equip_finished():
	return true

func is_unequip_finished():
	return true



# Update Wear
func update_item(action = "Refresh"):
	
	var item_data = {
		"Name" : item_name,
		"Image" : item_image
	}
	
	item_manager.update_hud(item_data)
