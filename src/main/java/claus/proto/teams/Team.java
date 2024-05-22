package claus.proto.teams;

import java.util.ArrayList;
import java.util.UUID;

public class Team
{
    UUID id;
    String name;

    ArrayList<User> coaches;

    ArrayList<User> gymnasts;
}
