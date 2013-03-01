<#include "/include/header.ftl">
<card title="${game_title}"><p>
你确定要删除好友【${friend.name}】吗，如果该好友为你的密友，删除后密友度将清除.<br/>
<@a href="/p?p_DF/${pid}/${friend.friendId}"/>立即删除</a><br/>
<@goback />
<@gogame />
</p></card>
</wml>