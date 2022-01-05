package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.commands;

import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.ExclusiveServerSelection;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.data.PlayerData;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.models.ServerTypeMapper;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.utils.MessageHelper;
import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Command;

public class CommandSelection extends Command {
    public CommandSelection() {
        super("selection", "bungeecord.command.selection");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(
                    new ComponentBuilder()
                            .append("잘못된 인자 개수")
                            .create()
            );
            return;
        }

        ServerType serverType;
        try {
            serverType = ServerType.valueOf(args[0]);
        } catch (IllegalArgumentException e) {
            MessageHelper.send(sender, "/오류/ 서버 유형 %s (은)는 존재하지 않습니다.", args[0]);
            return;
        }

        ServerInfo serverInfo = ServerTypeMapper.getServerInfo(serverType);
        if (serverInfo == null) {
            MessageHelper.send(sender, "/오류/ 서버 유형 %s 의 서버 연결 정보가 존재하지 않습니다.", args[0]);
            return;
        }

        ProxiedPlayer targetPlayer;
        PlayerData playerData;
        if (args.length >= 3 && args[1].equalsIgnoreCase("to")) {
            if (!sender.getName().equalsIgnoreCase("CONSOLE") && sender.hasPermission("bungeecord.command.selection.admin")) {
                sender.sendMessage("권한 없음");
                return;
            }

            playerData = ExclusiveServerSelection.getServerData()
                    .getPlayerDataOrCreate(args[2], false);
            if (playerData == null) {
                MessageHelper.send(sender, "/경고/ 플레이어 %s 의 데이터가 기존에 존재하지 않아 새로 생성합니다.", args[2]);
                playerData = ExclusiveServerSelection.getServerData()
                        .getPlayerDataOrCreate(args[2], true);
            }
        } else {
            ProxiedPlayer player;
            try {
                player = (ProxiedPlayer) sender;
            } catch (ClassCastException ex) {
                MessageHelper.send(sender, "/오류/ 콘솔에서 사용할 수 없는 명령어 형태입니다.");
                return;
            }
            playerData = ExclusiveServerSelection.getServerData().getPlayerDataOrCreate(player, true);
            if (playerData.getServerType() != ServerType.LOBBY) {
                MessageHelper.send(sender, "/오류/ 한 번 서버를 선택하면 바꿀 수 없습니다. 관리자에게 문의해주세요.");
                return;
            }
        }

        assert playerData != null;
        playerData.setServerType(serverType);
        MessageHelper.send(sender,
                "/성공/ 플레이어 %s 의 기본 연결 서버를 %s (으)로 설정했습니다.",
                playerData.getUsername(),
                playerData.getServerType()
        );

        targetPlayer = ProxyServer.getInstance().getPlayer(playerData.getUsername());
        if (targetPlayer != null) {
            targetPlayer.setReconnectServer(serverInfo);
            targetPlayer.connect(serverInfo, ServerConnectEvent.Reason.PLUGIN);

            MessageHelper.send(sender,
                    "/성공/ 접속중인 플레이어 %s 를 서버 %s (으)로 이동했습니다.",
                    playerData.getUsername(),
                    playerData.getServerType()
            );
        }
    }
}
