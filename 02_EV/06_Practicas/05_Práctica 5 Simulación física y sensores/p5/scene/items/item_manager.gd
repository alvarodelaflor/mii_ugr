extends Spatial


# Almaceenamos todos los items que puede tener el jugador
var all_items = {}

# Almacenamos los items que porta el jugador
var items = {}

#HUD
var hud

# Item actual
var current_item # Reference to the current weapon equipped
var current_item_slot = "Empty" # The current weapon slot

var changing_item = false
var unequipped_item = false

var item_index = 0 # For switching weapons through mouse wheel


func _ready():
	
	hud = owner.get_node("HUD")
	get_parent().get_node("Camera/RayCast").add_exception(owner) # Adds exception of player to the shooting raycast
	
	all_items = {
		"Unarmed" : preload("res://scene/items/nothing/nothing.tscn"),
		"Pistol_B" : preload("res://scene/items/something/deporte/deporte_a/zapatilla.tscn"),
		"Rifle_B" : preload("res://scene/items/something/agua/rifle_b/botella.tscn")
	}
	
	items = {
		"Empty" : $Unarmed,
		"Primary" : $Empty,
		"Secondary" : $Empty
	}
	
	# Initialize references for each weapons
	for w in items:
		if is_instance_valid(items[w]):
			weapon_setup(items[w])
	
	# Set current weapon to unarmed
	current_item = items["Empty"]
	change_item("Empty")
	
	# Disable process
	set_process(false)


# Initializes Weapon's values
func weapon_setup(w):
	w.weapon_manager = self
	w.player = owner
	w.ray = get_parent().get_node("Camera/RayCast")
	w.visible = false


# Process will be called when changing weapons
func _process(delta):
	
	if unequipped_item == false:
		if current_item.is_unequip_finished() == false:
			return
		
		unequipped_item = true
		
		current_item = items[current_item_slot]
		current_item.equip()
	
	if current_item.is_equip_finished() == false:
		return
	
	changing_item = false
	set_process(false)



func change_item(new_weapon_slot):
	
	if new_weapon_slot == current_item_slot:
		current_item.update_ammo() # Refresh
		return
	
	if is_instance_valid(items[new_weapon_slot]) == false:
		return
	
	current_item_slot = new_weapon_slot
	changing_item = true
	
	items[current_item_slot].update_ammo() # Updates the weapon data on UI, as soon as we change a weapon
	update_weapon_index()
	
	# Change Weapons
	if is_instance_valid(current_item):
		unequipped_item = false
		current_item.unequip()
	
	set_process(true)




# Scroll weapon change
func update_weapon_index():
	match current_item_slot:
		"Empty":
			item_index = 0
		"Primary":
			item_index = 1
		"Secondary":
			item_index = 2

func next_item():
	item_index += 1
	
	if item_index >= items.size():
		item_index = 0
	
	change_item(items.keys()[item_index])

func previous_item():
	item_index -= 1
	
	if item_index < 0:
		item_index = items.size() - 1
	
	change_item(items.keys()[item_index])

# Ammo Pickup
func add_ammo(amount):
	if is_instance_valid(current_item) == false || current_item.name == "Unarmed":
		return false
	
	current_item.update_ammo("add", amount)
	return true



# Add weapon to an existing empty slot
func add_weapon(weapon_data):
	
	if not weapon_data["Name"] in all_items:
		return
	
	if is_instance_valid(items["Primary"]) == false:
		
		# Instance the new weapon
		var weapon = Global.instantiate_node(all_items[weapon_data["Name"]], Vector3.ZERO, self)
		
		# Initialize the new weapon references
		weapon_setup(weapon)
		weapon.ammo_in_mag = weapon_data["Ammo"]
		weapon.extra_ammo = weapon_data["ExtraAmmo"]
		weapon.mag_size = weapon_data["MagSize"]
		weapon.transform.origin = weapon.equip_pos
		
		# Update the dictionary and change weapon
		items["Primary"] = weapon
		change_item("Primary")
		
		return
	
	if is_instance_valid(items["Secondary"]) == false:
		
		# Instance the new weapon
		var weapon = Global.instantiate_node(all_items[weapon_data["Name"]], Vector3.ZERO, self)
		
		# Initialize the new weapon references
		weapon_setup(weapon)
		weapon.ammo_in_mag = weapon_data["Ammo"]
		weapon.extra_ammo = weapon_data["ExtraAmmo"]
		weapon.mag_size = weapon_data["MagSize"]
		weapon.transform.origin = weapon.equip_pos
		
		# Update the dictionary and change weapon
		items["Secondary"] = weapon
		change_item("Secondary")
		
		return



# Will be called from player.gd
func drop_item():
	if current_item_slot != "Empty":
		current_item.drop_weapon()
		
		# Need to be set to Unarmed in order call change_weapon() function
		current_item = "Empty"
		current_item = items["Empty"]
		
		# Update HUD
		current_item.update_ammo()
		
		change_item("Empty")



# Switch Weapon / Replace Weapon
func switch_weapon(weapon_data):
	
	# Checks whether there's any empty slot available
	# If there is, then we simply add that new weapon to the empty slot
	for i in items:
		if is_instance_valid(items[i]) == false:
			add_weapon(weapon_data)
			return
	
	
	# If we are unarmed, and pickup a weapon
	# Then the weapon at the primary slot will be dropped and replaced with the new weapon
	if current_item.name == "Unarmed":
		items["Primary"].drop_weapon()
		yield(get_tree(), "idle_frame")
		add_weapon(weapon_data)
	
	
	# If the weapon to be picked up and the current weapon are same
	# Theb the ammo of the new weapon is added to the currently equipped weapon
	elif current_item.weapon_name == weapon_data["Name"]:
		add_ammo(weapon_data["Ammo"] + weapon_data["ExtraAmmo"])
	
	
	# If we already have an equipped weapon, then we drop it
	# And equip the new weapon
	else:
		drop_item()
		
		yield(get_tree(), "idle_frame")
		add_weapon(weapon_data)


# Interaction Prompt
func show_interaction_prompt(weapon_name):
	var desc = "Add Ammo" if current_item.weapon_name == weapon_name else "Coger"
	hud.show_interaction_prompt(desc)

func hide_interaction_prompt():
	hud.hide_interaction_prompt()


# Searches for weapon pickups, and based on player input executes further tasks (will be called from player.gd)
func process_item_pickup():
	var from = global_transform.origin
	var to = global_transform.origin - global_transform.basis.z.normalized() * 2.0
	var space_state = get_world().direct_space_state
	var collision = space_state.intersect_ray(from, to, [owner], 1)
	
	if collision:
		var body = collision["collider"]
		
		if body.has_method("get_weapon_pickup_data"):
			var weapon_data = body.get_weapon_pickup_data()
			
			show_interaction_prompt(weapon_data["Name"])
			
			if Input.is_action_just_pressed("interact"):
				switch_weapon(weapon_data)
				body.queue_free()
		else:
			hide_interaction_prompt()



# Update HUD
func update_hud(weapon_data):
	var weapon_slot = "1"
	
	match current_item_slot:
		"Empty":
			weapon_slot = "1"
		"Primary":
			weapon_slot = "2"
		"Secondary":
			weapon_slot = "3"
	
	hud.update_weapon_ui(weapon_data, weapon_slot)


