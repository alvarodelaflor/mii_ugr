tool
extends MultiMeshInstance

export var cube_per_side = 16 setget set_cube_per_side

func set_cube_per_side(new_cubes_per_side):
	cube_per_side = new_cubes_per_side
	update()
	
func update():
	setup_mutimesh()
	generate_sphere()
	
func setup_mutimesh():
	self.multimesh = MultiMesh.new()
	self.multimesh.transform_format = MultiMesh.TRANSFORM_3D
	
	self.multimesh.instance_count = cube_per_side * cube_per_side * cube_per_side
	self.multimesh.visible_instance_count = self.multimesh.instance_count
	
	var mesh = CubeMesh
	mesh.size = Vector3(1,1,1)
	self.multimesh.mesh = mesh
	
func generate_sphere():
	var cube_size = 1
	var cube_half_size = cube_size / 2.0
	var origin_distance = cube_per_side * cube_half_size - cube_half_size
	var origin = -Vector3(origin_distance, origin_distance, origin_distance)
	var index = 0
	
	for x in range(cube_per_side):
		for y in range(cube_per_side):
			for z in range(cube_per_side):
				var pos = origin * Vector3(x * cube_size, y * cube_size, z * cube_size)
				self.multimesh.set_instance_transform(index, Transform(Basis(), pos))
				index += 1
