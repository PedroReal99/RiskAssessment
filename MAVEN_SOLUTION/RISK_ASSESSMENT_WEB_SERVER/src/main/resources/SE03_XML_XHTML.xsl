<!--<?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>
    <!-- template SE03 -->
    <xsl:template match="/SE03">
        <html>
            <h4>
                <title>SE03:Resultado Obtido nos Casos Classificados como Concluidos.</title>
                Resultado Obtido
            </h4>
            <body>
                Result: <xsl:value-of select="Result"/>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>