<#include "/include/header.ftl">
<card title="${game_title}"><p>
道具赠送：<br/>
<#if itemType == '1'>
你打算送一个【${playerItem.item.name}】给你的好友【${otherPlayer.name}】吗？剩余${playerItem.amount}个。<br/>
<#else>
你要送几个【${playerItem.item.name}】给你的好友【${otherPlayer.name}】,剩余${playerItem.amount}个。<br/>
请输入数量：<input name="itemNum" maxlength="12" emptyok="false" type="text" /><br/>
</#if>
<anchor>确定<br/>
<@go href="/p?i_G/${pid}/" method="post"/>
<postfield name="1" value="$itemNum"/>
<postfield name="2" value="${playerItem.id}"/>
<postfield name="3" value="${itemType}"/>
<postfield name="4" value="${otherPlayer.id}"/>
<postfield name="5" value="${playerItem.item.id}"/>
</go>
</anchor>
<br/>
<@goback/>
</p>
</card>
</wml>