if [ "$1" = "" ]; then
	cd domain
	sh update.sh
	cd ..
	cd frontend
	sh update.sh
	cd ..
	cd matcher
	sh update.sh
	cd ..
	cd notifier
	sh update.sh
	cd ..
fi

if [ "$1" = "domain" ]; then
	cd domain
	sh update.sh
	cd ..
fi

if [ "$1" = "frontend" ]; then
	cd frontend
	sh update.sh
	cd ..
fi

if [ "$1" = "matcher" ]; then
	cd matcher
	sh update.sh
	cd ..
fi

if [ "$1" = "notifier" ]; then
	cd notifier
	sh update.sh
	cd ..
fi