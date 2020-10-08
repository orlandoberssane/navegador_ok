



import 'package:flutter/material.dart';
import 'package:flutter_meucrud/data/users_teste.dart';

class UserList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final users={...USERS_TESTE};
    return Scaffold(
      appBar: AppBar(
        title: Text("Lista de Usuário"),
      ),
      body: ListView.builder(
          itemCount: users.length,
          itemBuilder: (ctx, i) => Text(users.values.elementAt(i).name),
      ),
    );
  }
}
