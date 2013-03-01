<#include "/include/header.ftl">
<card title="${game_title}">
<p>
擂台：你已经成功设置擂台，你现在是擂主，请时刻关注你的对手出现，${num}秒内没有对手则自动退出擂台并返还参赛金；<br/>
如果在150秒无人来应对你的擂台斗神快报将会帮你发送广告，招人挑战！<br/>
<@a href="/p?f_CL/${pid}/shua/${type}"/>刷新</a><br/>
<@a href="/p?p_VS/${pid}/"/>状态</a> <@a href="/p?i_L/${pid}/1"/>物品</a> <@a href="/p?ca_L/${pid}/0"/>聊天</a><br/>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>
 