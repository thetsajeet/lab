#!/usr/bin/env python
from argparse import ArgumentParser, FileType
from configparser import ConfigParser

from confluent_kafka import Consumer

if __name__ == "__main__":
    # Parse the command line.
    parser = ArgumentParser()
    parser.add_argument("config_file", type=FileType("r"))
    args = parser.parse_args()

    # Parse the configuration.
    config_parser = ConfigParser()
    config_parser.read_file(args.config_file)
    config = dict(config_parser["default"])
    config.update(config_parser["consumer"])

    # Create Consumer instance
    consumer = Consumer(config)

    # Subscribe to topic
    topic = "peoms"
    consumer.subscribe([topic])

    # Poll for new messages from Kafka and print them.
    try:
        while True:
            msg = consumer.poll(1.0)
            if msg is None:
                print("Waiting...")
            elif msg.error():
                print("ERROR: %s")
            else:
                # Extract the (optional) key and value, and print.
                print(
                    "Consumed event from topic {topic}: key = {key} value = {value}".format(
                        topic=msg.topic(), key=msg.key(), value=msg.value()
                    )
                )
    except KeyboardInterrupt:
        pass
    finally:
        # Leave group and commit final offsets
        consumer.close()
