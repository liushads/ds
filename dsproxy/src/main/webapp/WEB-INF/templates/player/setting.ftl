<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
<#--<@a href="/p?se_LUS/${pid}/"/>装载招式</a><br/>-->
<@a href="/p?p_BPP/${pid}/0/"/>书签密码</a><br/>
<@a href="/p?p_IPP/${pid}/1/"/>道具密码</a><br/>
<@a href="/p?p_SS/${pid}/"/>战斗设置</a><br/>
<@a href="/p?p_Ps/${pid}/p/g/"/>找回密码</a><br/>
<@a href="/p?p_Ps/${pid}/p/s/"/>开启密码保护</a><br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>