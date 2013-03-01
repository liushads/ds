<#include "/include/header.ftl">
<card title="${game_title}"><p>
你可以把其他人加入到黑名单，加入黑名单后将不会收到对方的私聊消息，不能赠送装备.<br/>
已有黑名单列表：<br/>
<#if all_blacklist?exists>
<#list all_blacklist as ls>
<@a href="/p?p_DB/${pid}/${ls[0].id}/"/>移除</a>${ls[0].name}<br/>
</#list>
</#if>
<@goback />
<@gogame />
</p>
</card>
</wml>