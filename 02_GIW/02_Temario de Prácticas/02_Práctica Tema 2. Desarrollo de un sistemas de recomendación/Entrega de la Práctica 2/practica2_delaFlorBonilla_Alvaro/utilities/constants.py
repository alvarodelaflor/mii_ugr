class Constants:

    url_principal = './ml-100k'

    def get_url_u_item(self):
        return self.url_principal + '/' + 'u.item'

    def get_url_u_data(self):
        return self.url_principal + '/' + 'u.data'

    def get_url_u_user(self):
        return self.url_principal + '/' + 'u.user'
