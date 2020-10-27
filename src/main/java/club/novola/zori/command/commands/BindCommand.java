package club.novola.zori.command.commands;

import club.novola.zori.command.Command;

// TODO
public class BindCommand extends Command {
    public BindCommand() {
        super(new String[]{"bind", "b"}, "bind <Module> <Key>");
    }

    @Override
    public void exec(String args) throws Exception {

    }
}
