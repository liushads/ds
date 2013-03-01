<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${friend.name}向你发出离婚请求，等待你处理:<br/>
<@a href="/p?pm_LP/${pid}/${friend.id}/0"/>同意离婚</a>.<br/>
<@a href="/p?pm_LP/${pid}/${friend.id}/1"/>不同意</a><br/>
<@goback/>
<@gogame />
</p>
</card>
</wml>