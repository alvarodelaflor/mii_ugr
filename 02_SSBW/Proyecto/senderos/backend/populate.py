from mongoengine import connect, Document, EmbeddedDocument	
from mongoengine.fields import EmbeddedDocumentField, StringField, ListField, IntField, DateTimeField
from datetime import datetime

connect('senderos_web', host='mongo')

class Reviews(EmbeddedDocument):
	content = StringField(required=True)
	author     = StringField(max_length=120, required=True)
	date     = DateTimeField(default=datetime.now())

class Excursion(Document):
	name      = StringField(max_length=120, required=True)
	description = StringField(required=True)
	likes       = IntField(default=0)
	visits     = IntField(default=0)
	tags        = ListField(StringField(max_length=20))
	duration    = IntField(default=0)
	reviews = ListField(EmbeddedDocumentField(Reviews))


reviews = [
	{
		'content': 'Primer comentario',
	 	'author': 'Yo'
	},
	{
		'content': 'Otro comentario',
		'author': 'Pepito'
	}
]

excursion = Excursion(name="Prueba", description="asfd asf asdf", likes=1, 
                      tags=['fácil'], reviews=reviews)
excursion.save()