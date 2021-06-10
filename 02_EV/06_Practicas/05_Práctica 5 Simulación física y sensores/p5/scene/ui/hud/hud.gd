extends Control


var item_ui
var health_ui
var display_ui
var slot_ui


func _enter_tree():
	item_ui = $Background/ItemUI
	health_ui = $Background/HealthUI
	display_ui = $Background/Display/TextureRect
	slot_ui = $Background/Display/ItemSlot


func _ready():
	hide_interaction_prompt()


func update_item_ui(item_data, item_slot):
	slot_ui.text = item_slot
	display_ui.texture = item_data["Image"]
	
	if item_data["Name"] == "Unarmed":
		item_ui.text = ""
		return
	
	item_ui.text = item_data["Name"] + ": " + item_data["Ammo"] + "/" + item_data["ExtraAmmo"]


# Show/Hide interaction prompt
func show_interaction_prompt(description = "Interact"):
	$InteractionPrompt.visible = true
	$InteractionPrompt/Description.text = description
	$Crosshair.visible = false

func hide_interaction_prompt():
	$InteractionPrompt.visible = false
	$Crosshair.visible = true




