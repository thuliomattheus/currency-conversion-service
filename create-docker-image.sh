#!/bin/bash

# Cria a imagem a partir do dockerfile
docker build \
  -f build.Dockerfile `# Informa o filename do dockerfile` \
  -t currency-conversion-service `#Cria uma tag pra imagem criada com o nome informado` \
  . `# Informa o path do dockerfile`

# Roda a imagem criada no passo acima (se a imagem já existir, não precisa do comando acima, o docker build)
docker run \
  -d `# Roda o processo em segundo plano e mostra apenas o id do container na tela` \
  -p 8100:8100 `# Expõe a porta do host (antes dos dois pontos) a partir da porta do container (depois dos dois pontos)` \
  --name currency-conversion-service `# Cria uma tag pro container` \
  currency-conversion-service `# Nome da imagem que será usada na inicialização do container`
