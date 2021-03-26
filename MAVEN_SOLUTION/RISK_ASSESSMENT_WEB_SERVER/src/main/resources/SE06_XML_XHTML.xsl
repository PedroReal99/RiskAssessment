<!--<?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>
    <!-- template SE06 -->
    <xsl:template match="/SE06">
        <html>
            <h4>
                <title>SE06</title>
                Conhecer a disponibilidade atual do Servico de Avaliacao de Risco.
            </h4>
            <body>
                Result: <xsl:value-of select="Result"/>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>