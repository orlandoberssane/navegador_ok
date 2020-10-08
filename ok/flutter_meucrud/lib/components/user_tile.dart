import 'package:flutter/material.dart';
import 'package:flutter_meucrud/models/user.dart';
class UserTitle extends StatelessWidget {
  final User user;
  const UserTile(this.user);


  @override
  Widget build(BuildContext context) {
    final avatar = user.avatarUrl == null  || user.avatar

    return Container();

  }
}