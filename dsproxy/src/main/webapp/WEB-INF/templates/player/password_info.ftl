<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if action=='g'>
恭喜你成功找回密码！ <br/>
<#if book?exists>
您的书签密码是:${book}<br/>
</#if>
<#if item?exists>
您的道具密码是:${item}<br/>
</#if>
<@a href="/p?p_Ps/${pid}/m/"/>修改账号保护</a><br/>
<#elseif action=='s'>
您的密码保护已经设置成功，请牢记您刚才填写的回答。<br/>
<#elseif action=='m'>
密码保护修改成功！请牢记您刚才修改的回答！<br/>
<#elseif action=='w'>
密码保护修改失败！旧的密码保护回答输入错误！<br/>
<#elseif action=='wg'>
找密码失败！密码保护回答输入错误！<br/>
<#elseif action=='input'>
设置密码保护失败！密码保护回答输入错误！<br/>
<#elseif action=='wgg'>
找回密码失败，你还没有设置书签和道具密码！<br/>
</#if>
<br/>
<br/>
【声明】：官方在游戏中不会以任何形式向你索取你的账号信息请不要向任何人透露自己的信息<br/>
<@goback/>
</p>
</card>
</wml>